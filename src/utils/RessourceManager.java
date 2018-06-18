package utils;

import javafx.scene.image.Image;
import model.Color;
import model.TypeSweet;

public class RessourceManager {

    public static Image getAssets(Color color, TypeSweet type, boolean selected) {

        String result = "ressources/sprite_";
        result += color.name().toLowerCase();
        if(selected) {
            result += "_selected";
        }
        result += ".png";

       return new Image(result);

    }

}
