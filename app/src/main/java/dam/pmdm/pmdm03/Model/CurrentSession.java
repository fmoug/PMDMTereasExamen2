package dam.pmdm.pmdm03.Model;

public class CurrentSession {

    private static User _user;
    private static Order _order;

    public static Order getOrder() {
        return _order;
    }

    public static void setOrder(Order _order) {
        CurrentSession._order = _order;
    }

    public static User getUser() {
        return _user;
    }

    public static void RegisterUser(User user)
    {
        _user = user;

    }
}
