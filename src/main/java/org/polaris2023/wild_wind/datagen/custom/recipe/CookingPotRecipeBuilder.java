package org.polaris2023.wild_wind.datagen.custom.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import org.polaris2023.wild_wind.common.recipe.CookingPotRecipe;

import java.util.*;
import java.util.function.Consumer;

import static org.polaris2023.wild_wind.datagen.ModRecipeProvider.has;

public class CookingPotRecipeBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final ItemStack resultStack; // Neo: add stack result support
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private FluidStack stack = FluidStack.EMPTY;
    @Nullable
    private String group;

    public CookingPotRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
        this(category, new ItemStack(result, count));
    }

    public CookingPotRecipeBuilder(RecipeCategory category, ItemLike result, int count, Consumer<CookingPotRecipeBuilder> consumer) {
        this(category, new ItemStack(result, count), consumer);

    }

    public CookingPotRecipeBuilder stack(FluidStack stack) {
        this.stack = stack;
        return this;
    }

    public CookingPotRecipeBuilder add(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return unlockedBy(Arrays.stream(ingredient.getItems()).map(ItemStack::getItem).toArray(Item[]::new));
    }

    public CookingPotRecipeBuilder add(ItemLike... likes) {
        this.ingredients.add(Ingredient.of(Arrays.stream(likes).map(ItemLike::asItem).toArray(Item[]::new)));
        return unlockedBy(likes);
    }

    protected CookingPotRecipeBuilder unlockedBy(ItemLike... likes) {
        StringBuilder sb = new StringBuilder("has");
        switch (likes.length) {
            case 0 -> {
            }
            case 1 -> {
                ItemLike like = likes[0];
                unlockedBy(sb.append("_").append(BuiltInRegistries.ITEM.getKey(like.asItem())).toString().toLowerCase(Locale.ROOT), has(like));
            }
            default -> {
                for (ItemLike like : likes) {
                    ResourceLocation key = BuiltInRegistries.ITEM.getKey(like.asItem());
                    sb.append("_").append(key);
                }
                unlockedBy(sb.toString().toLowerCase(Locale.ROOT), has(likes));
            }
        }
        return this;
    }

    protected CookingPotRecipeBuilder unlockedBy(Item... likes) {
        StringBuilder sb = new StringBuilder("has");
        switch (likes.length) {
            case 0 -> {
            }
            case 1 -> {
                Item item = likes[0];
                unlockedBy(sb.append("_").append(BuiltInRegistries.ITEM.getKey(item)).toString().toLowerCase(Locale.ROOT), has(item));
            }
            default -> {
                for (Item like : likes) {
                    ResourceLocation key = BuiltInRegistries.ITEM.getKey(like);
                    sb.append("_").append(key);
                }
                unlockedBy(sb.toString().toLowerCase(Locale.ROOT), has(likes));
            }
        }
        return this;
    }

    public FluidStack stack() {
        return stack;
    }

    public CookingPotRecipeBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result = result.getItem();
        this.count = result.getCount();
        this.resultStack = result;
    }

    public CookingPotRecipeBuilder(RecipeCategory category, ItemStack result, Consumer<CookingPotRecipeBuilder> consumer) {
        this(category, result);
        consumer.accept(this);
    }

    public static CookingPotRecipeBuilder cooking(RecipeCategory category, ItemLike result) {
        return new CookingPotRecipeBuilder(category, result, 1);
    }

    public static CookingPotRecipeBuilder cooking(RecipeCategory category, ItemLike result, Consumer<CookingPotRecipeBuilder> consumer) {
        return new CookingPotRecipeBuilder(category, result, 1, consumer);
    }

    public static CookingPotRecipeBuilder cooking(RecipeCategory category, ItemLike result, int count) {
        return new CookingPotRecipeBuilder(category, result, count);
    }

    public static CookingPotRecipeBuilder cooking(RecipeCategory category, ItemLike result, int count, Consumer<CookingPotRecipeBuilder> consumer) {
        return new CookingPotRecipeBuilder(category, result, count);
    }

    public static CookingPotRecipeBuilder cooking(RecipeCategory category, ItemStack result) {
        return new CookingPotRecipeBuilder(category, result);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        CookingPotRecipe cookingPot = new CookingPotRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                this.resultStack,
                stack,
                this.ingredients
        );
        recipeOutput.accept(id, cookingPot, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }
}
