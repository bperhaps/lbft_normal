package model.state;

import network.Network;

public class States {
    private NetworkState networkState;
    private View view;
    private WaterMark waterMark;
    private SequenceNumber sequenceNumber;
    private SendBlockOrNot sendBlockOrNot;

    public States() {
        this.networkState = new NetworkState();
        this.view = new View(networkState);
        this.waterMark = new WaterMark();
        this.sequenceNumber = new SequenceNumber();
        this.sendBlockOrNot = new SendBlockOrNot();
    }

    public SendBlockOrNot getSendBlockOrNot() {
        return sendBlockOrNot;
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    public View getView() {
        return view;
    }

    public WaterMark getWaterMark() {
        return waterMark;
    }

    public SequenceNumber getSequenceNumber() {
        return sequenceNumber;
    }
}
