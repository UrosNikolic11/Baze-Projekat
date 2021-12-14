package error;

public class GetErrorView implements IError{

    private Error error;

    public GetErrorView() {
        error = Error.getInstance();
    }

    @Override
    public void errorHandler(String string) {

    }

    @Override
    public Error getError() {
        return error;
    }
}
