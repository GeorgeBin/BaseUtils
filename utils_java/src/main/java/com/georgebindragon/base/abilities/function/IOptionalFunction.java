package com.georgebindragon.base.abilities.function;

/**
 * author：George
 *
 * description：
 *
 * modification：
 */
public interface IOptionalFunction extends IFunction
{
    /**
     * function is available: static, available state should not change after apk build
     *
     * @return the function is available; true=available
     */
    boolean isFunctionAvailable();
}
