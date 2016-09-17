import os, os.path
import sys, stat

def find_archive_dir(parent_dir, tarfile):
    archive_dir = None
    
    for fn in os.listdir(parent_dir):
        if tarfile.startswith(fn):
        
            path = os.path.join(output_dir, fn)
            if os.path.isdir(path):
                timestamp = os.stat(path)[stat.ST_MTIME]
                if archive_dir is None or os.stat(archive_dir)[stat.ST_MTIME] < timestamp:
                    archive_dir = filepath
                
    return archive_dir
    
def build_redis(srcfile, outdir):
    dirpath, filename = os.path.split(srcfile)
     
    # extract source file into the same directory with tar.gz file.
    print "Extract source file into %s." % dirpath
     
    ret = os.system( 'tar xvfz %s -C %s' % (srcfile, dirpath) )
    if ret != 0:
        # print "Extract source code from %s failure: %s" % (srcfile, sys.exc_info()[0])
        raise RuntimeError("Extract source code from %s failure: %s" % (srcfile, sys.exc_info()[0]))
    
    build_dir = find_archive_dir(outdir, filename)
    print "Switch directory to %s." % build_dir
    os.chdir(build_dir)
     
    if not os.path.isdir(outdir):
        print "Outpath directory %s doesn't exist, create it." % outdir
        os.makedirs(outdir)
         
    # build and install
    print "Build and install Redgraph into %s." % outdir
    ret = os.system( "make PREFIX=%s install" % outdir )
    if ret != 0:
        # print "Build failure: %s" % sys.exc_info()[0]
        raise RuntimeError("Build failure: %s" % sys.exc_info()[0])
        
def make_structure(dirs):
    for d in dirs:
        if not os.path.isdir(d):
            print "Directory %s doesn't exist, create it." % d
            os.makedirs(d)
        
