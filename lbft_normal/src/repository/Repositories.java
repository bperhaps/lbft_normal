package repository;

import java.awt.*;

public class Repositories {
    private PublicKeyRepository publicKeyRepository;
    private PreprepareRepository preprepareRepository;
    private PrepareRepository prepareRepository;
    private RequestRepository requestRepository;
    private BlockRepository blockRepository;
    private TempBlockRepository tempBlockRepository;

    public Repositories() {
        this.publicKeyRepository = new PublicKeyRepository();
        this.preprepareRepository = new PreprepareRepository();
        this.prepareRepository = new PrepareRepository();
        this.requestRepository = new RequestRepository();
        this.blockRepository = new BlockRepository();
        this.tempBlockRepository = new TempBlockRepository();
    }

    public TempBlockRepository tempBlock() {
        return tempBlockRepository;
    }

    public PublicKeyRepository publicKey() {
        return publicKeyRepository;
    }

    public PreprepareRepository preprepare() {
        return preprepareRepository;
    }

    public PrepareRepository prepare() {
        return prepareRepository;
    }

    public RequestRepository request() {
        return requestRepository;
    }

    public BlockRepository block() {
        return blockRepository;
    }

}
