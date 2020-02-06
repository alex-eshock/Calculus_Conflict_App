package sample;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import javafx.geometry.Point2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BombAction extends Action implements KeyListener {

    boolean state = false;
    Point2D vec = Point2D.ZERO;
    ControllerManager controller;

    BombAction(ControllerManager controller) {
        this.controller = controller;
    }

    @Override
    Point2D statePoint2D() {
        ControllerState currState = controller.getState(0);
        vec = new Point2D(currState.rightStickX, currState.rightStickY);
        return vec;
    }

    @Override
    boolean state() {
        if(controller.getState(0).leftTrigger > 0.5 || controller.getState(0).rightTrigger > 0.5 ) {
            state = true;
        }
        return state;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //this sucks don't use
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'e') {
            state = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'e') {
            state = false;
        }
    }
}
