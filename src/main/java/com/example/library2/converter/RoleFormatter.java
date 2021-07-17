package com.example.library2.converter;

import com.example.library2.model.Role;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class RoleFormatter implements Formatter<Role> {
    @Override
    public Role parse(String text, Locale locale) throws ParseException {
       Role role = new Role();
       role.setPid(text);
        return role;
    }

    @Override
    public String print(Role object, Locale locale) {
        return object.getPid();
    }
}
