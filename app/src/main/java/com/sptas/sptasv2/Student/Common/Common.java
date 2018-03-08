package com.sptas.sptasv2.Student.Common;

import com.sptas.sptasv2.Student.Model.Question;
import com.sptas.sptasv2.Student.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Na'im Mansor on 17-Feb-18.
 */

public class Common {
    public static final String STR_PUSH = "pushNotification";
    public static String categoryId, categoryName;
    public static User currentUser;
    public static List<Question> questionList = new ArrayList<>();
}
