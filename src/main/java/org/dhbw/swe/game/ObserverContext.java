package org.dhbw.swe.game;

import java.util.Optional;

public class ObserverContext {

    private Context context;
    private Optional<Integer> fieldIndex;

    public ObserverContext(Context context, Optional<Integer> fieldIndex) {

        this.context = context;
        this.fieldIndex = fieldIndex;

    }

    public ObserverContext(Context context) {

        this.context = context;

    }

    public Context getContext() {

        return context;

    }

    public int getFieldIndex() {

        if(fieldIndex.isEmpty())
            throw new RuntimeException("Field Index not available!");

        return fieldIndex.get();

    }

}
