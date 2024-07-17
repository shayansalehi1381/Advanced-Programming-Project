package Controller;

import Model.Shot;
import Model.Epsilon;
import View.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EpsilonController implements KeyListener, MouseListener {
    private Epsilon epsilon;
    private boolean S_Key = false;
    private boolean W_Key = false;
    private boolean D_Key = false;
    private boolean A_Key = false;

    public EpsilonController(Epsilon epsilon) {
        this.epsilon = epsilon;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public Epsilon getEpsilon() {
        return epsilon;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                W_Key = true;
                handleVerticalMovement(-epsilon.getAcceleration(), S_Key);
                break;
            case KeyEvent.VK_S:
                S_Key = true;
                handleVerticalMovement(3, W_Key);
                break;
            case KeyEvent.VK_D:
                D_Key = true;
                handleHorizontalMovement(3, A_Key);
                break;
            case KeyEvent.VK_A:
                A_Key = true;
                handleHorizontalMovement(-epsilon.getAcceleration(), D_Key);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                W_Key = false;
                epsilon.setDirectionY(0);
                break;
            case KeyEvent.VK_S:
                S_Key = false;
                epsilon.setDirectionY(0);
                break;
            case KeyEvent.VK_D:
                D_Key = false;
                epsilon.setDirectionX(0);
                break;
            case KeyEvent.VK_A:
                A_Key = false;
                epsilon.setDirectionX(0);
                break;
        }
        Check4ButtonsClicked();
        epsilon.move();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Shot shot = new Shot(epsilon);
        epsilon.getShots().add(shot);
        shot.setTarget(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void handleVerticalMovement(int direction, boolean oppositeKey) {
        if (GamePanel.winTheGame) {
            resetDirection();
        } else {
            if (oppositeKey) {
                epsilon.setDirectionY(0);
            } else {
                epsilon.setDirectionY(direction);
            }
            epsilon.move();
        }
    }

    private void handleHorizontalMovement(int direction, boolean oppositeKey) {
        if (GamePanel.winTheGame) {
            resetDirection();
        } else {
            if (oppositeKey) {
                epsilon.setDirectionX(0);
            } else {
                epsilon.setDirectionX(direction);
            }
            epsilon.move();
        }
    }

    private void resetDirection() {
        epsilon.setDirectionX(0);
        epsilon.setDirectionY(0);
    }

    private void Check4ButtonsClicked() {
        if (!epsilon.isImpactTriangle() && !W_Key && !S_Key && !D_Key && !A_Key) {
            epsilon.setxSpeed(0);
            epsilon.setySpeed(0);
        }
    }
}
