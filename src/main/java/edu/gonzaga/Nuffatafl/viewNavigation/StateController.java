/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 04/06/2024
 */

package edu.gonzaga.Nuffatafl.viewNavigation;

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.viewHelpers.PlayerChangeRelay;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Manages the state of the overall program (which view is displayed),
 * Contains a MainView and GameManager to keep all relevant objects in one place
 */
public class StateController {
    /** Initializes with screen state of none, creates MainView and PropertyManager, sets up property change support */
    public StateController() {
        super();
        this.screen = Screen.none;
        this.previousScreen = Screen.none;
        this.screenChangeManager = new PropertyChangeSupport(this.screen);
        this.gameManager = new GameManager();
    }

    /** The GameManager that handles game logic, included here to keep everything together */
    public GameManager gameManager;

    /** Current screen the program's UI should show */
    private Screen screen;

    /** Previous screen that was displayed, used to go back to previous screen */
    private Screen previousScreen;

    /** Handles updating observers when new value change is published for screen */
    private final PropertyChangeSupport screenChangeManager;

    /** Handles relaying player changes to the gamePlay screen, because the two are otherwise disconnected */
    public PlayerChangeRelay playerChangeRelay;

    /** Changes program state to cause UI to show welcome screen */
    public void showWelcomeScreen() {
        this.changeState(Screen.welcome);
    }

    /** Changes program state to cause UI to show gameplay screen */
    public void startGame() {
        this.changeState(Screen.gameplay);
    }

    /** Changes program state to cause UI to show afterGame screen */
    public void endGame() {
        this.changeState(Screen.afterGame);
    }

    /** Changes program state to cause UI to show rules screen */
    public void showRules() {
        this.changeState(Screen.rules);
    }

    /** Changes program state to cause UI to show settings screen */
    public void showSettings() {
        this.changeState(Screen.settings);
    }

    /** Changes program state to cause UI to show previous screen */
    public void goToPreviousScreen() {
        if (this.previousScreen != null) {
            this.changeState(this.previousScreen);
        } else {
            this.changeState(Screen.welcome);
        }
    }

    /** Exits program (unnecessary but good to have if we want to add things to do on exit later) */
    public void endProgram() {
        System.exit(0);
    }

    /**
     * Adds an observer to be notified when the current screen is changed
     * @param listener Code to execute when current screen changes
     */
    public void onScreenChange(PropertyChangeListener listener) {
        this.screenChangeManager.addPropertyChangeListener(listener);
    }

    /** Gets the Screen representing the current screen that should be displayed */
    public Screen getCurrentScreen() {
        return this.screen;
    }

    /** Gets the Screen representing the screen that was previously displayed */
    public Screen getPreviousScreen() {
        return this.previousScreen;
    }

    /**
     * Changes the currently displayed screen and publishes the change to observers
     * @param newScreen the new screen to display
     */
    private void changeState(Screen newScreen) {
        this.previousScreen = this.screen;
        this.screen = newScreen;
        this.screenChangeManager.firePropertyChange("screen", this.previousScreen, this.screen);
    }
}