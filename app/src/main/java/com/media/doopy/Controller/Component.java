package com.media.doopy.Controller;

public abstract class Component implements IComponent{
    protected int get_id() {
        return 0;
    }
    @Override
    public boolean equals(Object obj) {
        Component check;

        check = (Component) obj;

        if(this.getClass().equals(obj.getClass()))
            if(this.get_id()==check.get_id())
                return true;

        return false;
    }
}
