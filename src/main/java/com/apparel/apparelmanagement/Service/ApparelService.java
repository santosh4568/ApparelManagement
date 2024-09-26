package com.apparel.apparelmanagement.Service;

import com.apparel.apparelmanagement.Model.Apparel;
import com.apparel.apparelmanagement.Repository.ApparelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApparelService {

    @Autowired
    ApparelRepo apparelRepo;

    public void deleteApparel(Long id){
        apparelRepo.deleteById(id);
    }

    public void updateApparel(Long id, String name, String type, String category, String username, String status){
        apparelRepo.updateApparel(id, name, type, category, username, status);
    }

    public void addApparel(String name, String type, String category, String username, String status){
        apparelRepo.save(new Apparel(name, type, category, username, status));
    }

    public List<Apparel> getApparel(){
        return apparelRepo.findAll();
    }

    public List<Apparel> getApparelByUsername(String username){
        return apparelRepo.findByUsername(username);
    }

    public Apparel getApparelById(Long id){
        return apparelRepo.findById(id).get();
    }

    public List<Apparel> getApparelByCategory(String category){
        return apparelRepo.findByCategory(category);
    }

    public List<Apparel> getApparelByType(String type){
        return apparelRepo.findByType(type);
    }

    public List<Apparel> getApparelByStatus(String status){
        return apparelRepo.findByStatus(status);
    }

}
