
#/u/prog/wwrepos/assembla/tusharkapilaNeev/neevmoh/trunk/starter/src/main/scripts/deploy.sh
#../../../../
/apps/tomcats/a/bin/shutdown.sh
cp -R Sel2inWeb/target/Sel2inWeb/* /apps/tomcats/a/webapps/ROOT/
/apps/tomcats/a/bin/startup.sh