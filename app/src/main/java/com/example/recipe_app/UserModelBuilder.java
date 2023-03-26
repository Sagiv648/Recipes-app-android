package com.example.recipe_app;

import java.util.ArrayList;

public class UserModelBuilder {
        UserModel user;

    public UserModelBuilder() {
        this.user = new UserModel();
    }

    public UserModelBuilder setUuid(String uuid)
        {

            user.setUuid(uuid);
            return this;
        }
        public UserModelBuilder setEmail(String email)
        {
            user.setEmail(email);
            return this;
        }
        public UserModelBuilder setUploadedRecipes(ArrayList<String> recs){

            if(user.getUploaded_recipes() == null)
                user.setUploaded_recipes(new ArrayList<>());
            user.getUploaded_recipes().addAll(recs);
            return this;
        }
        public UserModel build()
        {
            return user;
        }


}
