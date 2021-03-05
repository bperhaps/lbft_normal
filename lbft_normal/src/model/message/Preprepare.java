package model.message;

import model.crypto.Credential;
import model.state.States;

public class Preprepare extends ConsensusMessage {

    public Preprepare(byte[] blockInfoDigest, States states, Credential credential) {
        super(states.getSequenceNumber().get(),
                states.getView().get(),
                blockInfoDigest,
                credential.getPrimaryNumber()
        );
    }
}
