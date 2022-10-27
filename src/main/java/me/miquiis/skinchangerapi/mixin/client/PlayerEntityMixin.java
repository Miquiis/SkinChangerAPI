package me.miquiis.skinchangerapi.mixin.client;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow
    @Final
    private static Map<Pose, EntitySize> SIZE_BY_POSE;

    @Shadow @Final public static EntitySize STANDING_SIZE;

    @Inject(at = @At("HEAD"), method = "getSize", cancellable = true)
    private void _getSize(Pose poseIn, CallbackInfoReturnable<EntitySize> cir)
    {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.isChild())
        {
            cir.cancel();
            cir.setReturnValue(SIZE_BY_POSE.getOrDefault(poseIn, STANDING_SIZE).scale(0.5f));
        }
    }

    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void _getStandingEyeHeight(Pose poseIn, EntitySize sizeIn, CallbackInfoReturnable<Float> cir)
    {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.isChild())
        {
            cir.cancel();
            switch(poseIn) {
                case SWIMMING:
                case FALL_FLYING:
                case SPIN_ATTACK:
                    cir.setReturnValue(0.4F);
                case CROUCHING:
                    cir.setReturnValue(1.27F * 0.5f);
                default:
                    cir.setReturnValue(1.62F * 0.5f);
            }
        }
    }

}