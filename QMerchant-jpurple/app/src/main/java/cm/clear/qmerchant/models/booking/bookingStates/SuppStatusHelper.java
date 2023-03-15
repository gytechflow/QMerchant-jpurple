package cm.clear.qmerchant.models.booking.bookingStates;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cm.clear.qmerchant.models.category.Category;

public class SuppStatusHelper {
    private static final String PATTERN = "[A-Z0-9]{6,8}";
    private static final String DEFAULT_COLOR = "FFFFFF";

    public static int getColor(@NonNull String category_color) {
        String color = DEFAULT_COLOR;
        if (matchesPattern(category_color))
            color = category_color;
        return Color.parseColor("#" + color);
    }

    private static boolean matchesPattern(@Nullable String category_color) {
        if (TextUtils.isEmpty(category_color))
            return false;
        if (!category_color.toUpperCase().matches(PATTERN))
            return false;
        return true;
    }

    @NonNull
    public static Category getMainCategory(@NonNull List<Category> categories) {
        int highest_fk_parent = Integer.MIN_VALUE;
        Category main_category = Category.getNullCategory();
        for (Category category : categories) {
            int parent_id = category.getFkParent();
            if (parent_id > highest_fk_parent) {
                main_category = category;
                highest_fk_parent = parent_id;
            }
        }
        return main_category;
    }

    @NonNull
    public static List<Category> getCategoriesToRemove(@NonNull List<Category> categories, @NonNull Category category_to_add) {
        int lowest_fk_parent = Integer.MIN_VALUE;
        Category main_category = getMainCategory(categories);
        Category conflicting_category = getMainCategory(categories);
        boolean isChild = main_category.getFkParent().equals(Integer.parseInt(category_to_add.getId()));
        boolean isParent = main_category.getId().equalsIgnoreCase(String.valueOf(category_to_add.getFkParent()));
        boolean isSibling = main_category.getFkParent().equals(category_to_add.getFkParent());
        if (isParent)
            return new ArrayList<>();
        if (isChild){
            List<Category> children = new ArrayList<>();
            addChildren(children, category_to_add, categories);
            return children;
        }
        if (isSibling)
            return new ArrayList<>(Collections.singletonList(main_category));
        for (Category category : categories) {
            if (category_to_add.getFkParent().equals(category.getFkParent())) {
                conflicting_category = category;
                break;
            }
        }
        return getCategoryChain(conflicting_category, categories);
    }

    @NonNull
    public static List<Category> getCategoryChain(@NonNull Category category_to_find, @NonNull List<Category> categories) {
        List<Category> categoryList = new ArrayList<>();
        addCategoryAndParents(categoryList, category_to_find, categories);
        addChildren(categoryList, category_to_find, categories);
        categoryList.add(category_to_find);
        return categoryList;
    }

    private static void addCategoryAndParents(List<Category> categoryList, Category category, List<Category> categories) {
        Category parentCategory = getParentCategory(category, categories);
        if (!parentCategory.getId().equals(Category.getNullCategory().getId())) {
            addCategoryAndParents(categoryList, parentCategory, categories);
            categoryList.add(parentCategory);
            categoryList.add(category);
        }
    }

    private static void addChildren(List<Category> categoryList, Category category, List<Category> categories) {
        for (Category childCategory : categories) {
            if (childCategory.getFkParent() == Integer.parseInt(category.getId())) {
                categoryList.add(childCategory);
                addChildren(categoryList, childCategory, categories);
            }
        }
    }

    @NonNull
    private static Category getParentCategory(Category category, List<Category> categories) {
        for (Category parentCategory : categories) {
            if (parentCategory.getId().equals(String.valueOf(category.getFkParent()))) {
                return parentCategory;
            }
        }
        return Category.getNullCategory();
    }

    @NonNull
    public static List<Category> getCategoriesToDisplay(@NonNull List<Category> all_categories, @NonNull List<Category> my_categories) {
        Category my_category = getMainCategory(my_categories);
        Category my_parent_category = getParentMainCategory(all_categories, my_category);
        List<Category> categories_to_display = new ArrayList<>();
        for (Category single_category : all_categories) {
            boolean isSibling = single_category.getFkParent().equals(my_category.getFkParent());
            boolean isChild = single_category.getFkParent().equals(Integer.parseInt(my_category.getId()));
            if (isSibling || isChild)
                categories_to_display.add(single_category);

            if (!my_parent_category.getId().equals(Category.getNullCategory().getId())) {
                boolean isSiblingToParent = single_category.getFkParent().equals(my_parent_category.getFkParent());
                if (isSiblingToParent)
                    categories_to_display.add(single_category);
            }
        }
        return categories_to_display;
    }

    @NonNull
    private static Category getParentMainCategory(@NonNull List<Category> my_categories, Category main_category) {
        for (Category category : my_categories) {
            if (category.getId().equals(String.valueOf(main_category.getFkParent())))
                return category;
        }
        return Category.getNullCategory();
    }
}
