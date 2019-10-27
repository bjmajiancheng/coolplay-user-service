package com.coolplay.user.common.utils;

import java.math.BigDecimal;

/**
 * Created by majiancheng on 2019/10/25.
 */
public class DistanceUtil {

    /**
     * 获得两点间距离
     *
     * @param posX
     * @param posY
     * @param currPosX
     * @param currPosY
     * @return
     */
    public static BigDecimal getDistance(BigDecimal posX, BigDecimal posY, BigDecimal currPosX, BigDecimal currPosY) {
        double distance = Math.sqrt(Math
                .abs((posX.subtract(currPosX)).multiply(posX.subtract(currPosX)).add(posY.subtract(currPosY))
                        .multiply(posY.subtract(currPosY)).doubleValue()));

        return new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
