package cn.hydcraft.hydronyasama.core.nsasm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Cross-version NSASM compatibility runtime. Supports the core instruction set from the original
 * NSDN/NyaSamaCore NSASM VM including variable definition, arithmetic, bitwise, comparison, control
 * flow (labels &amp; jumps), stack operations, and I/O.
 */
public final class NsasmEngine {

  /** Maximum number of instructions executed before aborting (prevents infinite loops). */
  private static final int MAX_STEPS = 1_000_000;

  public ExecutionResult execute(String source) {
    if (source == null || source.trim().isEmpty()) {
      return new ExecutionResult(false, "empty program", Collections.<String>emptyList());
    }

    String[] rawLines = source.replace("\r", "").split("\n");

    // First pass: trim comments and collect labels.
    Map<String, Integer> labels = new LinkedHashMap<>();
    String[] lines = new String[rawLines.length];
    for (int i = 0; i < rawLines.length; i++) {
      String line = trimComment(rawLines[i]);
      if (line.endsWith(":")) {
        labels.put(line.substring(0, line.length() - 1).trim(), i);
        lines[i] = "";
      } else {
        lines[i] = line;
      }
    }

    // Second pass: execute.
    List<String> outputs = new ArrayList<>();
    Map<String, Double> registers = new LinkedHashMap<>();
    Deque<Double> stack = new ArrayDeque<>();
    double cmpFlag = 0;
    int lineNo = 0;
    int steps = 0;
    try {
      while (lineNo < lines.length) {
        String line = lines[lineNo];
        int displayLine = lineNo + 1; // 1-based for error messages
        if (line.isEmpty()) {
          lineNo++;
          continue;
        }
        if (++steps > MAX_STEPS) {
          throw new IllegalStateException(
              "line " + displayLine + ": execution limit exceeded (" + MAX_STEPS + " steps)");
        }
        String[] tokens = line.trim().split("\\s+");
        String op = tokens[0].toLowerCase(Locale.ROOT);

        // --- nop ---
        if ("nop".equals(op)) {
          lineNo++;
          continue;
        }

        // --- halt / end ---
        if ("halt".equals(op) || "end".equals(op)) {
          break;
        }

        // --- set / let / mov ---
        if ("set".equals(op) || "let".equals(op) || "mov".equals(op)) {
          requireArgCount(tokens, 3, displayLine, op);
          registers.put(tokens[1], parseValue(tokens[2], registers));
          lineNo++;
          continue;
        }

        // --- arithmetic: add, sub, mul, div, mod ---
        if ("add".equals(op)
            || "sub".equals(op)
            || "mul".equals(op)
            || "div".equals(op)
            || "mod".equals(op)) {
          requireArgCount(tokens, 3, displayLine, op);
          String target = tokens[1];
          double left = registers.getOrDefault(target, 0.0D);
          double right = parseValue(tokens[2], registers);
          if ("add".equals(op)) {
            registers.put(target, left + right);
          } else if ("sub".equals(op)) {
            registers.put(target, left - right);
          } else if ("mul".equals(op)) {
            registers.put(target, left * right);
          } else if ("div".equals(op)) {
            if (Math.abs(right) < 1.0E-9D) {
              throw new IllegalArgumentException("line " + displayLine + ": div by zero");
            }
            registers.put(target, left / right);
          } else {
            if (Math.abs(right) < 1.0E-9D) {
              throw new IllegalArgumentException("line " + displayLine + ": mod by zero");
            }
            registers.put(target, (double) (Math.round(left) % Math.round(right)));
          }
          lineNo++;
          continue;
        }

        // --- neg, inc, dec (single-operand arithmetic) ---
        if ("neg".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          String target = tokens[1];
          registers.put(target, -registers.getOrDefault(target, 0.0D));
          lineNo++;
          continue;
        }
        if ("inc".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          String target = tokens[1];
          registers.put(target, registers.getOrDefault(target, 0.0D) + 1.0D);
          lineNo++;
          continue;
        }
        if ("dec".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          String target = tokens[1];
          registers.put(target, registers.getOrDefault(target, 0.0D) - 1.0D);
          lineNo++;
          continue;
        }

        // --- bitwise: and, or, xor, not, shl, shr ---
        if ("and".equals(op) || "or".equals(op) || "xor".equals(op)) {
          requireArgCount(tokens, 3, displayLine, op);
          String target = tokens[1];
          long left = Math.round(registers.getOrDefault(target, 0.0D));
          long right = Math.round(parseValue(tokens[2], registers));
          long result;
          if ("and".equals(op)) {
            result = left & right;
          } else if ("or".equals(op)) {
            result = left | right;
          } else {
            result = left ^ right;
          }
          registers.put(target, (double) result);
          lineNo++;
          continue;
        }
        if ("not".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          String target = tokens[1];
          long val = Math.round(registers.getOrDefault(target, 0.0D));
          registers.put(target, (double) (~val));
          lineNo++;
          continue;
        }
        if ("shl".equals(op) || "shr".equals(op)) {
          requireArgCount(tokens, 3, displayLine, op);
          String target = tokens[1];
          long val = Math.round(registers.getOrDefault(target, 0.0D));
          int shift = (int) Math.round(parseValue(tokens[2], registers));
          if ("shl".equals(op)) {
            registers.put(target, (double) (val << shift));
          } else {
            registers.put(target, (double) (val >> shift));
          }
          lineNo++;
          continue;
        }

        // --- cmp ---
        if ("cmp".equals(op)) {
          requireArgCount(tokens, 3, displayLine, op);
          double left = parseValue(tokens[1], registers);
          double right = parseValue(tokens[2], registers);
          cmpFlag = Math.signum(left - right);
          lineNo++;
          continue;
        }

        // --- jump instructions ---
        if ("jmp".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          lineNo = resolveJumpTarget(tokens[1], labels, displayLine);
          continue;
        }
        if ("jz".equals(op) || "je".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          if (cmpFlag == 0) {
            lineNo = resolveJumpTarget(tokens[1], labels, displayLine);
          } else {
            lineNo++;
          }
          continue;
        }
        if ("jnz".equals(op) || "jne".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          if (cmpFlag != 0) {
            lineNo = resolveJumpTarget(tokens[1], labels, displayLine);
          } else {
            lineNo++;
          }
          continue;
        }
        if ("jg".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          if (cmpFlag > 0) {
            lineNo = resolveJumpTarget(tokens[1], labels, displayLine);
          } else {
            lineNo++;
          }
          continue;
        }
        if ("jl".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          if (cmpFlag < 0) {
            lineNo = resolveJumpTarget(tokens[1], labels, displayLine);
          } else {
            lineNo++;
          }
          continue;
        }

        // --- stack operations ---
        if ("push".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          stack.push(parseValue(tokens[1], registers));
          lineNo++;
          continue;
        }
        if ("pop".equals(op)) {
          requireArgCount(tokens, 2, displayLine, op);
          if (stack.isEmpty()) {
            throw new IllegalStateException("line " + displayLine + ": pop from empty stack");
          }
          registers.put(tokens[1], stack.pop());
          lineNo++;
          continue;
        }

        // --- print / echo ---
        if ("print".equals(op) || "echo".equals(op)) {
          String body = line.length() <= op.length() ? "" : line.substring(op.length()).trim();
          outputs.add(resolvePrintable(body, registers));
          lineNo++;
          continue;
        }

        throw new IllegalArgumentException(
            "line " + displayLine + ": unsupported opcode '" + op + "'");
      }
    } catch (RuntimeException ex) {
      return new ExecutionResult(false, ex.getMessage(), new ArrayList<>(outputs), registers);
    }
    if (outputs.isEmpty()) {
      outputs.add("ok");
    }
    return new ExecutionResult(true, null, new ArrayList<>(outputs), registers);
  }

  /**
   * Resolves a jump target to a 0-based line index. The target may be a label name or a 0-based
   * numeric line number.
   */
  private static int resolveJumpTarget(
      String target, Map<String, Integer> labels, int displayLine) {
    Integer idx = labels.get(target);
    if (idx != null) {
      return idx;
    }
    try {
      return Integer.parseInt(target);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException(
          "line " + displayLine + ": unknown label '" + target + "'");
    }
  }

  private static String trimComment(String line) {
    String out = line == null ? "" : line.trim();
    int hash = out.indexOf('#');
    if (hash >= 0) {
      out = out.substring(0, hash).trim();
    }
    int slash = out.indexOf("//");
    if (slash >= 0) {
      out = out.substring(0, slash).trim();
    }
    return out;
  }

  private static void requireArgCount(String[] tokens, int expected, int lineNo, String op) {
    if (tokens.length < expected) {
      throw new IllegalArgumentException(
          "line " + lineNo + ": opcode '" + op + "' expects " + (expected - 1) + " args");
    }
  }

  private static double parseValue(String token, Map<String, Double> registers) {
    Double reg = registers.get(token);
    if (reg != null) {
      return reg;
    }
    try {
      return Double.parseDouble(token);
    } catch (RuntimeException ex) {
      throw new IllegalArgumentException("unknown symbol '" + token + "'");
    }
  }

  private static String resolvePrintable(String body, Map<String, Double> registers) {
    if (body == null || body.isEmpty()) {
      return "";
    }
    if ((body.startsWith("\"") && body.endsWith("\""))
        || (body.startsWith("'") && body.endsWith("'"))) {
      return body.substring(1, body.length() - 1);
    }
    Double reg = registers.get(body);
    if (reg != null) {
      if (Math.abs(reg - Math.rint(reg)) < 1.0E-9D) {
        return Long.toString(Math.round(reg));
      }
      return String.format(Locale.ROOT, "%.6f", reg);
    }
    return body;
  }

  public static final class ExecutionResult {
    private final boolean success;
    private final String error;
    private final List<String> outputLines;
    private final Map<String, Double> registers;

    public ExecutionResult(boolean success, String error, List<String> outputLines) {
      this(success, error, outputLines, Collections.<String, Double>emptyMap());
    }

    public ExecutionResult(
        boolean success, String error, List<String> outputLines, Map<String, Double> registers) {
      this.success = success;
      this.error = error;
      this.outputLines = outputLines;
      this.registers = new LinkedHashMap<>(registers);
    }

    public boolean isSuccess() {
      return success;
    }

    public String getError() {
      return error;
    }

    public List<String> getOutputLines() {
      return outputLines;
    }

    public Map<String, Double> getRegisters() {
      return Collections.unmodifiableMap(registers);
    }
  }
}
