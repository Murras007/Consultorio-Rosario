package api.exception;

import api.exception.enums.UserException;
import com.google.gson.JsonElement;

public class UserTranslateException {

    public static class Auth {

        public static ExceptionBody translate (String exceptionString) {
            ExceptionBody exceptionBody = new ExceptionBody();
            if (exceptionString.contains(String.valueOf(UserException.USER_NOT_FOUND))){
                exceptionBody.title = "Utilizador nao encontrado";
                exceptionBody.message = "Verifique se escreveu correctamente o nome de utilizador";
            }

            if (exceptionString.contains(String.valueOf(UserException.PASSWORD_WRONG))){
                exceptionBody.title = "Senha invalida";
                exceptionBody.message = "A senha que inseriu nao esta correcta";
            }

            return exceptionBody;
        }
    }

}
