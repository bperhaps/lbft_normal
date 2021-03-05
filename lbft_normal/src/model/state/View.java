package model.state;

import model.crypto.Credential;

public class View {
    private long view;
    private long previousView = -1;
    private NetworkState networkState;

    public View(NetworkState networkState) {
        this.networkState = networkState;
    }

    public void add() {
        view++;
    }

    public long get() {
        return view;
    }

    public long getPrimary() {
        return view % networkState.getN();
    }

    public boolean isPrimary(Credential credential) {
        return getPrimary() == credential.primaryNumber;
    }

    public boolean isBetween(long view, NetworkState networkState) {
        return view <= this.view + networkState.getN();
    }

    public long getPreviousView() {
        return previousView;
    }

    public void update() {
        previousView = view;
    }

    public boolean AmINewPrimary(Credential credential) {
        if(!isPrimary(credential)) {
            return false;
        }
        if(previousView == view) return false;
        return true;
    }
}
