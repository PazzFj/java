package net.pazz.design.visitor;

/**
 * @author pazz
 * @create 2018/1/23
 * @note ComputerPart 电脑零件
 */
public interface ComputerPart {

    /**
     * accept 接受
     * @param computerPartVisitor
     */
    void accept(ComputerPartVisitor computerPartVisitor);

}
