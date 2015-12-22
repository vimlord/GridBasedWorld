/**
 * InventoryPanel.java
 * 
 * Christopher Hittner (c) 2015
 */
package io;

import io.content.Button;
import io.content.IOContent;
import io.content.IOShape;
import io.content.IOTextLabel;
import items.Inventory;
import items.Item;
import items.Loadout;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * An InventoryPanel contains the panels required to represent an Inventory screen
 *
 * @author Christopher Hittner
 */
public class LoadoutPanel  extends IOPanel {

    public LoadoutPanel(Loadout load) {
        super("INV");
        
        //Provides a background.
        BlankPanel background = new BlankPanel("BKGRND");
        
        //Creates and adds buttons
        ContentPanel inventory = new ContentPanel("INV_DISP");
        ArrayList<IOContent> invContent = generateContent(load, 0);
        for(IOContent c : invContent)
            inventory.addContent(c);
        
        //Puts the Items onto the Inventory in order.
        this.sendToTop(background);
        this.sendToTop(inventory);
    }
    
    /**
     * Creates the items for the interactive layer of the InventoryPanel
     * @param inv The Inventory being displayed.
     * @param itemsSkipped How many items to skip over.
     * @return The list of IOContents.
     */
    private static ArrayList<IOContent> generateContent(Loadout load, int itemsSkipped) {
        
        //Grabs to inside Inventory.
        Inventory inv = load.getInventory();
        
        //Holds the list of buttons.
        ArrayList<IOContent> content = new ArrayList<>();
        
        //Adds the return to main screen button.
        content.add(new Button("IO_MANAGER.SET_DISPLAY[game_main]", "<-", 20, 20, 40, 40));
        
        //Up button
        content.add(new Button("IO_MANAGER.SET_DISPLAY[inventory|" + ((itemsSkipped-1)&0x7fffffff) + "]", "^"
                , Interface.getInterface().getWidth() - 60, 20, 40, 40));
        
        //Down button
        content.add(new Button("IO_MANAGER.SET_DISPLAY[inventory|" + (Math.min(itemsSkipped+1, inv.size())) + "]", "v"
                , Interface.getInterface().getWidth() - 60, Interface.getInterface().getHeight() - 60, 40, 40));
        
        //Tracks the number of items that were skipped.
        int numInvalid = itemsSkipped;
        
        //Calculates the maximum number of items to display. Two sets of 80px margins are provided.
        int maxDisplayed = (Interface.getInterface().getHeight() - (80*2))/40;
        
        /*
         *(For each item in the list, attempt to add a Button and label for it.
         *The labels will be 40px thick. If all of the required labels are inserted, then
         *cease adding them.
        */
        for(int i = itemsSkipped; i < inv.size() && maxDisplayed < i-numInvalid; i++) {
            Item item = inv.get(i);
            
            //If there is an item, take care of it.
            if(item != null) {
                int idx = i - numInvalid;
                
                //Labels
                IOTextLabel itemName = new IOTextLabel(item.NAME()       , 100, 90 + 40*idx, 16);
                IOTextLabel itemDesc = new IOTextLabel(item.DESCRIPTION(), 100, 105 + 40*idx, 12);
                IOShape box = new IOShape(new Rectangle(0, 80 + 40*idx, Interface.getInterface().getWidth(), 40));
                
                //Buttons
                Button drop =  new Button("ERROR[Inventory drop button not written yet.]" , "DROP", 10, 80 + 40*idx, 40, 40);
                Button equip = new Button("ERROR[Inventory equip button not written yet.]", "EQUIP", 50, 80 + 40*idx, 40, 40);
                
                //Adds the Items.
                content.add(box);
                content.add(itemName);
                content.add(itemDesc);
                content.add(drop);
                content.add(equip);
                
                
            } else numInvalid++; // Otherwise, increase the number of skipped items because this one was skipped.
        }
        
        
        
        return content;
        
    }

    @Override
    public void clickOperation(int x, int y) {
        
    }
    
    
}
