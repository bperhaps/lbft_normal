package network.handler;

import model.crypto.Credential;
import model.message.Request;
import model.message.SignedMessage;
import model.state.States;

import network.Network;
import repository.Repositories;

import java.util.Base64;

public class RequestHandler implements MessageHandler {

    @Override
    public void handle(SignedMessage signedMessage, Network network, States states, Repositories repositories, Credential credential) {
        Request request = (Request) signedMessage.getMessage();

        repositories.request().add(
                Base64.getEncoder().encodeToString(request.getDigest()),
                request
        );


        states.getSendBlockOrNot().start(states, network, repositories, credential);
    }
}
