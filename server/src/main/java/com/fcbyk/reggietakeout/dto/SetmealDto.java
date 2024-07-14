package com.fcbyk.reggietakeout.dto;


import com.fcbyk.reggietakeout.entity.Setmeal;
import com.fcbyk.reggietakeout.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
