package model.state;

import model.blockchain.Block;
import model.crypto.Credential;
import model.message.Preprepare;
import model.message.Request;
import model.message.SignedMessage;
import network.Network;
import network.handler.Operation;
import repository.Repositories;

public class SendBlockOrNot {
    public void start(States states, Network network, Repositories repositories, Credential credential) {
        Block block = null;
        synchronized (this) {
            if (!states.getView().AmINewPrimary(credential)) {
                ///if(from == 1) System.out.println(credential.primaryNumber + " view : " + states.getView().get() + " : " + states.getView().getPreviousView());
                return;
            }

            Request[] requests = repositories.request().getRequests();
            if(requests == null) return;


            long newHeight = repositories.block().getHighest() + 1;
            block = new Block(newHeight, requests, repositories.block().getTop());
            repositories.tempBlock().add(block.getCurrentHash(), block);

            states.getView().update();
        }
        Preprepare preprepare = new Preprepare(block.getCurrentHashBytes(), states, credential);
        SignedMessage signedPreprepare = SignedMessage.of(preprepare, block, credential);
        network.broadcast(signedPreprepare);

        new Thread(()->Operation.handle(signedPreprepare, network, states, repositories, credential)).start();
    }
}
