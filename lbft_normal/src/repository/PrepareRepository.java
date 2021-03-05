package repository;

import model.message.ConsensusMessage;
import model.message.Prepare;
import model.state.States;

public class PrepareRepository extends Repository<Prepare> {

    public boolean isPrepared(ConsensusMessage prepare, States states, Repositories repositories, boolean debug) {
        if(!isOverThreshold(prepare, states)) {
            if(debug) System.out.println("threshold in view" + prepare.getView());
            return false;
        }
        if(!hasSamePreprepareAndBlockInfo(prepare, states, repositories, debug)) {
            if(debug) System.out.println("same in view" + prepare.getView());
            return false;
        }
        return true;
    }
}
