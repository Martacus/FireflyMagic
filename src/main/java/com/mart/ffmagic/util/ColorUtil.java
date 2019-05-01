package com.mart.ffmagic.util;

import com.mart.ffmagic.entity.EntityFirefly;

import java.util.HashMap;
import java.util.Map;

public class ColorUtil {

    public static Map<EntityFirefly.FireflyType, RgbColor> fireflyColors = new HashMap<>();

    public static void init(){
        fireflyColors.put(EntityFirefly.FireflyType.FAIRY, new RgbColor(231, 255, 79));
        fireflyColors.put(EntityFirefly.FireflyType.FOREST, new RgbColor(55, 201, 58));
        fireflyColors.put(EntityFirefly.FireflyType.MOUNTAIN, new RgbColor(193, 155, 82));
        fireflyColors.put(EntityFirefly.FireflyType.DEMON, new RgbColor(201, 60, 55));
        fireflyColors.put(EntityFirefly.FireflyType.ICE, new RgbColor(79, 255, 255));
        fireflyColors.put(EntityFirefly.FireflyType.VOID, new RgbColor(15, 10, 2));
        fireflyColors.put(EntityFirefly.FireflyType.EARTH, new RgbColor(88, 83, 75));
    }

}
