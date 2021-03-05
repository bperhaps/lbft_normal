package network.handler;

import model.blockchain.service.BlockGenerator;
import model.crypto.Credential;
import model.message.Prepare;
import model.message.SignedMessage;
import model.state.States;
import network.Network;
import repository.Repositories;

public class PrepareHandler implements MessageHandler {
    @Override
    public void handle(SignedMessage signedMessage, Network network, States states, Repositories repositories, Credential credential) {
        Prepare prepare = (Prepare) signedMessage.getMessage();

        if (!states.getView().isBetween(prepare.getView(), states.getNetworkState())) {
            System.out.println("ERROR preprepare: view");
            return;
        }

        Prepare storedPrepare = repositories
                .prepare().find(prepare.getIdentity());

        if (storedPrepare != null && !storedPrepare.equals(prepare)) {
            System.out.println("Error prepare: prepare already exist in same view and sequence");
            return;
        }

        if (!states.getWaterMark().isBetweenIn(prepare.getSequenceNumber())) {
            System.out.println("ERROR preprepare: sequenceNumber");
            return;
        }

        synchronized (this) {
            repositories.prepare().add(prepare.getIdentity(), prepare);

            if (repositories.prepare().isPrepared(prepare, states, repositories, false) &&
                    !states.getNetworkState().isSentMessage(prepare.getIdentity())) {

                BlockGenerator.findBlockAndAddBlock(prepare, repositories, credential);
                states.getView().add();

                states.getNetworkState().sentMessage(prepare.getIdentity());
            }
        }

        states.getSendBlockOrNot().start(states, network, repositories, credential);
    }
}
