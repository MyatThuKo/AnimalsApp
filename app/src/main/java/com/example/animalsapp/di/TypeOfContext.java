package com.example.animalsapp.di;

import javax.inject.Qualifier;

@Qualifier
public @interface TypeOfContext {
    public static final String CONTEXT_APP = "Application Context";

    public static final String CONTEXT_ACTIVITY = "Activity Context";

    String value() default "";
}
