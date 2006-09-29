/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.dxo.builder.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.seasar.extension.dxo.command.DxoCommand;
import org.seasar.extension.dxo.command.impl.BeanToBeanDxoCommand;
import org.seasar.extension.dxo.converter.ConverterFactory;

/**
 * @author koichik
 * 
 */
public class BeanToBeanDxoCommandBuilder extends AbstractDxoCommandBuilder {

    protected ConverterFactory converterFactory;

    public void setConverterFactory(final ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public DxoCommand createDxoCommand(final Class dxoClass, final Method method) {
        final Class[] parameterTypes = method.getParameterTypes();
        final int parameterSize = parameterTypes.length;
        if (parameterSize != 1 && parameterSize != 2) {
            return null;
        }

        final Class sourceType = parameterTypes[0];
        final Class sourceElementClass = getElementTypeOfListFromParameterType(
                method, 0);
        final Class destType = parameterSize == 1 ? method.getReturnType()
                : parameterTypes[1];
        final Class destElementClass = getElementTypeOfListFromDestination(method);
        if (sourceType.isArray()) {
            if (destType.isArray()) {
                return new BeanToBeanDxoCommand(dxoClass, method,
                        converterFactory, getAnnotationReader(), sourceType
                                .getComponentType(), destType
                                .getComponentType());
            } else if (List.class.isAssignableFrom(destType)
                    && destElementClass != null) {
                return new BeanToBeanDxoCommand(dxoClass, method,
                        converterFactory, getAnnotationReader(), sourceType
                                .getComponentType(), destElementClass);
            }
        } else if (List.class.isAssignableFrom(sourceType)
                && sourceElementClass != null) {
            if (destType.isArray()) {
                return new BeanToBeanDxoCommand(dxoClass, method,
                        converterFactory, getAnnotationReader(),
                        sourceElementClass, destType.getComponentType());
            } else if (List.class.isAssignableFrom(destType)) {
                return new BeanToBeanDxoCommand(dxoClass, method,
                        converterFactory, getAnnotationReader(),
                        sourceElementClass, destElementClass);
            }
        } else {
            if (!destType.isArray() && !List.class.isAssignableFrom(destType)) {
                return new BeanToBeanDxoCommand(dxoClass, method,
                        converterFactory, getAnnotationReader(), sourceType,
                        destType);
            }
        }
        return null;
    }

}