package com.mart.ffmagic.entity;

import com.hrznstudio.titanium.TitaniumClient;
import com.mart.ffmagic.FireflyMagic;
import com.mart.ffmagic.item.ModItems;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class EntityFirefly extends EntityFlying {

    public static final DataParameter<String> TYPE = EntityDataManager.createKey(EntityFirefly.class, DataSerializers.STRING);

    public EntityFirefly(World worldIn) {
        super(FireflyMagic.FIREFLY, worldIn);
        this.setSize(0.1F, 0.1F);
        this.moveHelper = new FireflyMoveHelper(this);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TYPE, FireflyType.FOREST.name());
    }


    @Override
    public void tick() {
        TitaniumClient.particleRenderer.spawnParticle(getEntityWorld(), "com.mart.ffmagic.particle.particlefirefly", getPosition().getX(), getPosition().getY(), getPosition().getZ(), 0, 0, 0, 1000);
        super.tick();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return true;
    }

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 65;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(5, new EntityFirefly.AIRandomFly(this));
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == ModItems.firefly_jar) {
            player.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, 1.0F, 1.0F);

            ItemStack resultStack;

            FireflyType type = FireflyType.valueOf(getDataManager().get(TYPE));
            switch(type){
                case FOREST:
                    resultStack = new ItemStack(ModItems.firefly_jar_forest);
                    break;
                case VOID:
                    resultStack = new ItemStack(ModItems.firefly_jar_void);
                    break;
                case FAIRY:
                    resultStack = new ItemStack(ModItems.firefly_jar_fairy);
                    break;
                case MOUNTAIN:
                    resultStack = new ItemStack(ModItems.firefly_jar_mountain);
                    break;
                case EARTH:
                    resultStack = new ItemStack(ModItems.firefly_jar_earth);
                    break;
                case DEMON:
                    resultStack = new ItemStack(ModItems.firefly_jar_demon);
                    break;
                case ICE:
                    resultStack = new ItemStack(ModItems.firefly_jar_ice);
                    break;
                default:
                    resultStack = new ItemStack(ModItems.firefly_jar_forest);
            }

            if(!player.abilities.isCreativeMode){
                itemstack.shrink(1);
            }
            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, resultStack);
            } else if (!player.inventory.addItemStackToInventory(resultStack)) {
                player.dropItem(resultStack, false);
            }

            this.remove(false);

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    static class AIRandomFly extends EntityAIBase {
        private final EntityFirefly parentEntity;

        public AIRandomFly(EntityFirefly firefly) {
            this.parentEntity = firefly;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();
            if (!entitymovehelper.isUpdating()) {
                return true;
            } else {
                double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 2f);
            double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
            double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 0.01D);
        }
    }

    static class FireflyMoveHelper extends EntityMoveHelper {
        private final EntityFirefly parentEntity;
        private int courseChangeCooldown;

        public FireflyMoveHelper(EntityFirefly firefly) {
            super(firefly);
            this.parentEntity = firefly;
        }

        public void tick() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                double d0 = this.posX - this.parentEntity.posX;
                double d1 = this.posY - this.parentEntity.posY;
                double d2 = this.posZ - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                    d3 = (double) MathHelper.sqrt(d3);
                    if (this.isNotColliding(this.posX, this.posY, this.posZ, d3)) {
                        this.parentEntity.motionX += d0 / d3 * 0.02D;
                        this.parentEntity.motionY += d1 / d3 * 0.02D;
                        this.parentEntity.motionZ += d2 / d3 * 0.02D;
                    } else {
                        this.action = EntityMoveHelper.Action.WAIT;
                    }
                }

            }
        }

        /**
         * Checks if entity bounding box is not colliding with terrain
         */
        private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
            double d0 = (x - this.parentEntity.posX) / p_179926_7_;
            double d1 = (y - this.parentEntity.posY) / p_179926_7_;
            double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
            AxisAlignedBB axisalignedbb = this.parentEntity.getBoundingBox();

            for(int i = 1; (double)i < p_179926_7_; ++i) {
                axisalignedbb = axisalignedbb.offset(d0, d1, d2);
                if (!this.parentEntity.world.isCollisionBoxesEmpty(this.parentEntity, axisalignedbb)) {
                    return false;
                }
            }

            return true;
        }
    }


    public enum FireflyType {
        FAIRY,
        FOREST,
        MOUNTAIN,
        DEMON,
        ICE,
        VOID,
        EARTH
    }
}
