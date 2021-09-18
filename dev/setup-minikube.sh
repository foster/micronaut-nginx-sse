shopt -s expand_aliases

manifest_root=$( cd "$(dirname "$0")" ; pwd -P )/minikube-templates
alias k="minikube kubectl --"

# nginx
k delete -f $manifest_root/nginx/nginx-svc.yaml --ignore-not-found
k delete -f $manifest_root/nginx/nginx-cm.yaml --ignore-not-found
k delete -f $manifest_root/nginx/nginx-tls-secret.yaml --ignore-not-found
k apply -f $manifest_root/nginx/nginx-cm.yaml
k apply -f $manifest_root/nginx/nginx-svc.yaml
k apply -f $manifest_root/nginx/nginx-tls-secret.yaml

minikube service nginx --url
