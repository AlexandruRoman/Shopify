/**
 * Created by Alex on 1/4/2017.
 */
public interface Visitor {
    void visit(BookDepartment bookDepartment);
    void visit(MusicDepartment musicDepartment);
    void visit(SoftwareDepartment softwareDepartment);
    void visit(VideoDepartment videoDepartment);
}
