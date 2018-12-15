package myobserver;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function:
 * @Return:
 */
public interface Function<Result, Param> {
    Result function(Param... data);
}
