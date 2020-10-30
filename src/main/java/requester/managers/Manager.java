package requester.managers;

import models.Price;

public interface Manager {

    Price getPrice();

    void setPrice();

    void setMetadata();
}
