package PropAssess;

import java.util.List;

/**
 * Template class for an element of a tab
 * 
 * @author Sera Vallee <3045024>
 */
public abstract class TabTemplate {
    //determines if class has been updated for the current search
    public boolean needsUpdate;
    
    /**
     * Update the values/charts/etc. inside the TabElement
     * 
     * @param subset 
     */
    abstract void update(List<Data>[] dataset, Statistics[] stats);
    
}
