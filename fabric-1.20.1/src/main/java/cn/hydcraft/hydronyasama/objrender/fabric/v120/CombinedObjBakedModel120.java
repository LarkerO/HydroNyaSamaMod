package cn.hydcraft.hydronyasama.objrender.fabric.v120;

import com.google.common.base.Suppliers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class CombinedObjBakedModel120 implements BakedModel, FabricBakedModel {
  private final List<BakedModel> delegates;
  private final List<FabricBakedModel> fabricDelegates;
  private final TextureAtlasSprite particle;
  private final Supplier<List<BakedQuad>[]> combinedQuadCache;

  CombinedObjBakedModel120(List<BakedModel> delegates) {
    this.delegates = List.copyOf(delegates);
    List<FabricBakedModel> fabrics = new ArrayList<>();
    for (BakedModel model : this.delegates) {
      fabrics.add((FabricBakedModel) model);
    }
    this.fabricDelegates = List.copyOf(fabrics);
    this.particle = this.delegates.get(0).getParticleIcon();
    this.combinedQuadCache = Suppliers.memoize(this::buildCombinedQuads);
  }

  @SuppressWarnings("unchecked")
  private List<BakedQuad>[] buildCombinedQuads() {
    List<BakedQuad>[] result = new List[7];
    for (int i = 0; i < 7; i++) {
      Direction dir = i < 6 ? Direction.values()[i] : null;
      RandomSource random = RandomSource.create();
      List<BakedQuad> all = new ArrayList<>();
      for (BakedModel delegate : delegates) {
        all.addAll(delegate.getQuads(null, dir, random));
      }
      result[i] = all.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(all);
    }
    return result;
  }

  @Override
  public boolean isVanillaAdapter() {
    return false;
  }

  @Override
  public void emitBlockQuads(
      BlockAndTintGetter blockView,
      BlockState state,
      BlockPos pos,
      Supplier<RandomSource> randomSupplier,
      RenderContext context) {
    for (FabricBakedModel delegate : fabricDelegates) {
      delegate.emitBlockQuads(blockView, state, pos, randomSupplier, context);
    }
  }

  @Override
  public void emitItemQuads(
      ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
    for (FabricBakedModel delegate : fabricDelegates) {
      delegate.emitItemQuads(stack, randomSupplier, context);
    }
  }

  @Override
  public @NotNull List<BakedQuad> getQuads(
      @Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
    return combinedQuadCache.get()[ModelHelper.toFaceIndex(direction)];
  }

  @Override
  public boolean useAmbientOcclusion() {
    return delegates.get(0).useAmbientOcclusion();
  }

  @Override
  public boolean isGui3d() {
    return delegates.get(0).isGui3d();
  }

  @Override
  public boolean usesBlockLight() {
    return delegates.get(0).usesBlockLight();
  }

  @Override
  public boolean isCustomRenderer() {
    return false;
  }

  @Override
  public TextureAtlasSprite getParticleIcon() {
    return particle;
  }

  @Override
  public ItemTransforms getTransforms() {
    return delegates.get(0).getTransforms();
  }

  @Override
  public ItemOverrides getOverrides() {
    return ItemOverrides.EMPTY;
  }
}
