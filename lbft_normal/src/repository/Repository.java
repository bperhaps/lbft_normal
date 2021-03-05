package repository;

import model.blockchain.Block;
import model.message.ConsensusMessage;
import model.message.Preprepare;
import model.state.States;
import network.Network;

import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Repository<T> {
    protected ConcurrentHashMap<String, T> repository = new ConcurrentHashMap<>();

    public T find(String name) {
        return repository.getOrDefault(name, null);
    }

    public void add(String name, T value) {
        if (repository.containsKey(name)) {
            return;
        }

        repository.put(name, value);
    }

    public void delete(String name) {
        repository.remove(name);
    }

    public void modify(String name, T value) {
        if (!repository.containsKey(name)) {
            return;
        }

        repository.put(name, value);
    }

    public String createIdentityWithPrimaryNumber(long primaryNumber, ConsensusMessage message, String className) {
        return String.join(Network.DELIMITER,
                String.valueOf(primaryNumber),
                String.valueOf(message.getView()),
                String.valueOf(message.getSequenceNumber()),
                className,
                Base64.getEncoder().encodeToString(message.getBlockHash())
        );
    }

    protected boolean hasSamePreprepareAndBlockInfo(ConsensusMessage message,States states, Repositories repositories, boolean debug) {
        String preprepareKey = createIdentityWithPrimaryNumber(message.getView()%states.getNetworkState().getN() ,message, Preprepare.class.getName());
        Preprepare preprepare = repositories.preprepare().find(preprepareKey);

        if(preprepare == null) {
            if(debug) System.out.println("preprepare " + states.getView().get());
            return false;
        }

        Block blockInfo = repositories.tempBlock().find(
                Base64.getEncoder().encodeToString(message.getBlockHash()));

        if(blockInfo == null) {
            if(debug) System.out.println("blockInfo");
            return false;
        }

        if(preprepare.getSequenceNumber() == message.getSequenceNumber() &&
                preprepare.getView() == message.getView() &&
                Arrays.compare(preprepare.getBlockHash(), message.getBlockHash()) == 0) {
            return true;
        }

        return false;
    }

    protected boolean isOverThreshold(ConsensusMessage message, States states) {
        int count = 0;
        for (int i = 0; i < states.getNetworkState().getN(); i++) {
            String key = createIdentityWithPrimaryNumber(i, message, message.getClass().getName());

            if (repository.containsKey(key) && repository.get(key).equals(message)) {
                count++;
            }
        }

        return count >= 2 * states.getNetworkState().getF() + 1;
    }

    public int size() {
        return repository.size();
    }
}
