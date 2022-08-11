package api.request.entity;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class PredictionRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(String result ){
        Prediction prediction = new Prediction(result);
        em.persist(prediction);
        em.flush();
    }

}
