package org.distshell.client.components;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;


public class UnorderedListWidget extends ComplexPanel {

    public UnorderedListWidget() {
        setElement(Document.get().createULElement());
    }

    public void setId(String id) {
        getElement().setId(id);
    }

    public void add(Widget w) {
        ListItemWidget li = new ListItemWidget(w);
        super.add(li, getElement());
    }

    public void addListItem(Widget listItem) {
        super.add(listItem, getElement());
    }

    public List<Widget> getAllWrappedWidgets() {
        List<Widget> allWidgets = new ArrayList<Widget>();
        WidgetCollection wc = super.getChildren();
        for (Widget widget : wc) {
            if (widget instanceof ListItemWidget)
                allWidgets.add(((ListItemWidget) widget).getWidget());
        }
        return allWidgets;
    }

    static class ListItemWidget extends SimplePanel {

        public ListItemWidget() {
            super(Document.get().createLIElement());
        }

        public ListItemWidget(String html) {
            this();
            getElement().setInnerText(html);
        }

        public ListItemWidget(Widget w) {
            this();
            this.add(w);
        }
    }

    public boolean contains(Widget widget) {
        return getAllWrappedWidgets().contains(widget) || getChildren().contains(widget);
    }

    /**
     * Removes the liw or the wrapped widget
     *
     * @param widget
     * @return if it was found and removed
     */
    @Override
    public boolean remove(Widget widget) {
        WidgetCollection wc = super.getChildren();
        for (Widget widget1 : wc) {
            if (widget1 instanceof ListItemWidget) {
                ListItemWidget liw = (ListItemWidget) widget1;
                if (widget.equals(liw.getWidget())) {
                    super.remove(widget1);
                    return true;
                }
            }
        }
        return super.remove(widget);
    }

}
