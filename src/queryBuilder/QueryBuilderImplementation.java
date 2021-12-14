package queryBuilder;

import error.GetErrorView;
import error.IError;
import queryBuilder.kompajler.Compiler;
import queryBuilder.kompajler.CompilerImplementation;
import queryBuilder.validator.Validator;
import queryBuilder.validator.ValidatorImplementation;

import java.util.List;

public class QueryBuilderImplementation implements Queryuilder{
    private IError error;
    private Validator validator;
    private Compiler kompajler;

    public QueryBuilderImplementation(Validator validator, Compiler kompajler) {
        this.validator = validator;
        this.kompajler = kompajler;
        this.error = new GetErrorView();
    }


    @Override
    public String buildQuery(String s) {
        List<String> greske = validator.validate(s);
        if(greske.isEmpty())
            return kompajler.compaile(s);
        else{
            for(int i = 0; i<greske.size();i++){
                error.getError().errorHandler(greske.get(i));
            }

            return null;
        }
    }
}