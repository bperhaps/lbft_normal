package repository;

import model.blockchain.service.BlockGenerator;
import model.message.Request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RequestRepository extends Repository<Request> {

    public Request[] getRequests() {
        if (repository.size() < BlockGenerator.REQUEST_THRESHOLD) return null;

        List<Request> result = new ArrayList<>();

        Iterator<String> iterator = repository.keySet().iterator();
        for (int i = 0; i < BlockGenerator.REQUEST_THRESHOLD && iterator.hasNext(); i++) {
            String key = iterator.next();
            result.add(repository.get(key));
        }

        return result.toArray(new Request[result.size()]);
    }
}
