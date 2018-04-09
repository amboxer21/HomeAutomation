class LightStatusesController < ActionController::Base

  skip_before_action :very_authenticity_token

  def index
    @light_status = LightStatus.new
  end

  def new
    @light_status = LightStatus.all
  end
	
  def update_state
    LightStatus.all.each do |s| 
      Rails.logger.debug "debugging - s.ip: #{s.ip}"
      s.update_columns(state: @state)
      Rails.logger.debug "debugging - @state: #{@state}"
      @state.to_s.match(/1/) ? onn_state(s.ip) : off_state(s.ip)
    end
  end

  def create_state_and_update
    LightStatus.create(state: @state, ip: @ip)
    update_state
  end

  def create

    @ip    = params[:ip]
    @state = params[:state]

    @light_status = LightStatus.find_by(ip: @ip)
    @light_status.nil? ? create_state_and_update : update_state

    respond_to do |format|
      if @light_status.save && !@light_status.nil?
        format.json { render nothing: true, status: :created }
      else
        format.json { render json: @light_status.errors, status: :unprocessable_entity }
      end
    end
  end    

  def send_signal(ip, sig)
    `bin/client #{ip} 9486 "#{sig}"`
  end

  def onn_state(ip)
    Rails.logger.debug "debugging - on_state(ip)"
    send_signal(ip, "onn") if is_alive?(ip)
  end

  def off_state(ip)
    Rails.logger.debug "debugging - off_state(ip)"
    send_signal(ip, "off") if is_alive?(ip)
  end

  def is_alive?(ip)
    return `ping -w1 -c1 #{ip}`.match(/, 0% packet loss/) ? true : false
  end

  private
  def lights_status_params
    params.require(:light_status).permit(:id, :state)
  end

end
