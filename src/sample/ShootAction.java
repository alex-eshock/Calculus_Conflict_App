package sample;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import javafx.geometry.Point2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShootAction extends Action implements KeyListener {

    private boolean state;
    private Point2D vec;
    ControllerManager controller;
    int controllerIndex;

    boolean leftKeyPressed = false;
    boolean rightKeyPressed = false;
    boolean downKeyPressed = false;
    boolean upKeyPressed = false;

    ShootAction(ControllerManager controller, int controllerIndex) {
        this.controller = controller;
        this.controllerIndex = controllerIndex;
    }

    @Override
    Point2D statePoint2D() {
        ControllerState currState = controller.getState(controllerIndex);
        if (currState.rightStickMagnitude > 0.2) {
            vec = new Point2D(currState.rightStickX, currState.rightStickY);
        }
        return vec;
    }


    @Override
    boolean state() {
        return state;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //this sucks farts
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 40)
            upKeyPressed = true;
        if (e.getKeyCode() == 39)
            rightKeyPressed = true;
        if (e.getKeyCode() == 37)
            leftKeyPressed = true;
        if (e.getKeyCode() == 38)
            downKeyPressed = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 40)
            upKeyPressed = false;
        if (e.getKeyCode() == 39)
            rightKeyPressed = false;
        if (e.getKeyCode() == 37)
            leftKeyPressed = false;
        if (e.getKeyCode() == 38)
            downKeyPressed = false;
    }

    private void updateVector() {

        //cursed
        if (leftKeyPressed && upKeyPressed) {
            vec = new Point2D(-Math.sqrt(2) / 2, Math.sqrt(2) / 2);
        } else if (leftKeyPressed && downKeyPressed) {
            vec = new Point2D(-Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
        } else if (rightKeyPressed && downKeyPressed) {
            vec = new Point2D(Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
        } else if (rightKeyPressed && upKeyPressed) {
            vec = new Point2D(Math.sqrt(2) / 2, Math.sqrt(2) / 2);
        } else if (upKeyPressed) {
            vec = new Point2D(0, -1);
        } else if (rightKeyPressed) {
            vec = new Point2D(1, 0);
        } else if (downKeyPressed) {
            vec = new Point2D(0,-1);
        } else if (leftKeyPressed) {
            vec = new Point2D(-1,0);
        }
    }

}
