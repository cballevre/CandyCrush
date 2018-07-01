package view;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Direction;
import model.Sweet;
import utils.RessourceManager;

import java.util.Observable;
import java.util.Observer;

public class SweetView extends ImageView implements Observer {

    Sweet sweet;

    public SweetView(Sweet sweet) {
        this.sweet = sweet;
        init();
    }

    public void init() {
        sweet.addView(this);
        setImage(RessourceManager.getAssets(sweet.getColor(), sweet.getType(), sweet.isSelected()));
    }

    public Sweet getSweet() {
        return sweet;
    }

    public void setSweet(Sweet sweet) {
        this.sweet = sweet;
    }

    @Override
    public void update(Observable o, Object arg) {

        setImage(RessourceManager.getAssets(sweet.getColor(), sweet.getType(), sweet.isSelected()));

    }
}
