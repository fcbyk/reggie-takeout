package com.fcbyk.reggietakeout.dto;


import com.fcbyk.reggietakeout.entity.Dish;
import com.fcbyk.reggietakeout.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
