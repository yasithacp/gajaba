package org.gajaba.rule.compiler.functions;

import org.gajaba.group.KeySeparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MinFunction {


    public static List<String> execute(String variable, List<String> agents, Map<Object, String> cache, KeySeparator separator) {
        boolean isFirst = true;
        boolean isNumerical = false;
        double selectedNumber = Double.MAX_VALUE;
        String selectedString = null;
        String selectedMember = null;

        for (Map.Entry<Object, String> cacheEntry : cache.entrySet()) {
            Object key = separator.getKey(cacheEntry.getKey());
            if (variable.equals(key)) {
                String memberId = separator.getMemberTokenId(cacheEntry.getKey());
                if (agents.indexOf(memberId) > -1) {
                    String value = cacheEntry.getValue();
                    if (isFirst) {
                        selectedMember = memberId;
                        isFirst = false;

                        try {
                            selectedNumber = Double.valueOf(value);
                            isNumerical = true;
                        } catch (NumberFormatException e) {
                             selectedString = value;
                        }
                    }else{
                        if(isNumerical){
                            try {
                                double currentNumber = Double.valueOf(value);
                                if(currentNumber<selectedNumber){
                                    selectedMember = memberId;
                                    selectedNumber = currentNumber;
                                }
                            } catch (NumberFormatException e) {
                                //do nothing
                            }
                        }else{
                            if(selectedString.compareTo(value)>0){
                                selectedMember = memberId;
                                selectedString = value;
                            }
                        }
                    }

                }
            }
        }
        List<String> answer = new ArrayList<>(1);
        answer.add(selectedMember);
        return answer;
    }
}
