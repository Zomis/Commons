package net.zomis.custommap.view.general;

import net.zomis.custommap.model.ITileModel;

/**
 * A simple layout that do not do any layouting on its own. It can be used as
 * the view for the board on which we layout all the pieces manually.
 */
public abstract class ViewContainer<TM extends ITileModel<?>> {
    protected boolean scrollEnabled = true;
    public abstract void addViewObject(ViewObject object);
    public abstract void removeViewObject(ViewObject object);
    public abstract void repaint();
}
