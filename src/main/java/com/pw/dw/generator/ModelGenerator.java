package com.pw.dw.generator;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class ModelGenerator<T> {

    private PodamFactory factory = new PodamFactoryImpl();
    private Class<T> classOfGenericType;

    public ModelGenerator(Class<T> classOfGenericType){
        this.classOfGenericType = classOfGenericType;
    }

    public T generate() {
        return factory.manufacturePojo(classOfGenericType);
    }
}
