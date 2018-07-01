package utils;

import javafx.scene.image.Image;
import model.Color;
import model.TypeSweet;

import java.io.InputStream;
import java.net.URL;

public class RessourceManager {

    public static Image getAssets(Color color, TypeSweet type, boolean selected) {

        String result = "/ressources/img/sprite_";
        result += color.name().toLowerCase();
        if(selected) {
            result += "_selected";
        }
        result += ".png";
        InputStream url = RessourceManager.class.getResourceAsStream(result);

       return new Image(url);

    }

}
